package org.codepay.common.spring;

import java.io.IOException;
import java.util.Iterator;

import org.apache.ibatis.session.SqlSessionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

/**
 *  重写SqlSessionFactoryBean，读取多个mybatis配置文件并合并
 * @author lisai
 */
public class MySqlSessionFactoryBean extends SqlSessionFactoryBean {

    private Logger LOGGER = LoggerFactory.getLogger(MySqlSessionFactoryBean.class);

    private Resource[] configLocations;

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {

        if (configLocations == null || configLocations.length == 0) {
            LOGGER.error("configLocations 配置信息为空");
            return null;
        }

        ByteArrayResource newResource;
        Resource resourceRoot;
        try {
            SAXReader reader = new SAXReader();
            reader.setValidation(false);
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            resourceRoot = configLocations[0];
            // 取得第一个文档的Document对象
            Document mainDocument = reader.read(resourceRoot.getInputStream());
            // 如果有两个以上mybatis的xml配置文件
            for (int i = 1, j = configLocations.length; i < j; i++) {
                Resource targetResource = configLocations[i];
                Document targetDocument = reader.read(targetResource.getInputStream());
                // 合并xml
                mergeXml(mainDocument, targetDocument);
            }

            newResource = new ByteArrayResource(mainDocument.asXML().getBytes("utf-8"));
        } catch (Exception e) {
            throw new IOException("merge mybatis.cfg.xml error：", e);
        }

        // 设置config的resource
        setConfigLocation(newResource);
        SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();

        return sqlSessionFactory;

    }

    /**
     * 合并mybatis.cfg.xml的alias节点和mapper节点
     *
     * @param mainDocument
     * @param targetDocument
     */
    private void mergeXml(Document mainDocument, Document targetDocument) {

        // 取得根结点
        Element rootEle = mainDocument.getRootElement();
        // 取得根结点
        Element targetEle = targetDocument.getRootElement();

        Element aliasesEle = (Element) rootEle.selectSingleNode("typeAliases");
        Element mappersEle = (Element) rootEle.selectSingleNode("mappers");

        Element t_aliasesEle = (Element) targetEle.selectSingleNode("typeAliases");
        Element t_mappersEle = (Element) targetEle.selectSingleNode("mappers");
        @SuppressWarnings("rawtypes")
        Iterator t_aliasesIter = t_aliasesEle.elementIterator("typeAlias");
        @SuppressWarnings("rawtypes")
        Iterator t_mappersIter = t_mappersEle.elementIterator("mapper");

        // 添加aliase节点到住document
        while (t_aliasesIter.hasNext()) {
            Element itemEle = (Element) t_aliasesIter.next();
            createAliasEle(aliasesEle, itemEle);
        }
        // 添加mapper节点到住document
        while (t_mappersIter.hasNext()) {
            Element itemEle = (Element) t_mappersIter.next();
            createTypeEle(mappersEle, itemEle);
        }
    }

    /**
     * 创建alias节点
     *
     * @param aliasesEle
     * @param itemEle
     */
    private void createAliasEle(Element aliasesEle, Element itemEle) {
        Element newElement = DocumentHelper.createElement("typeAlias");
        String type = itemEle.attributeValue("type");
        String alias = itemEle.attributeValue("alias");
        newElement.addAttribute("type", type);
        newElement.addAttribute("alias", alias);
        aliasesEle.add(newElement);
    }

    /**
     * 创建mapper节点
     *
     * @param mappersEle
     * @param itemEle
     */
    private void createTypeEle(Element mappersEle, Element itemEle) {
        Element newElement = DocumentHelper.createElement("mapper");
        String resource = itemEle.attributeValue("resource");
        newElement.addAttribute("resource", resource);
        mappersEle.add(newElement);
    }

    public void setConfigLocations(Resource[] configLocations) {
        this.configLocations = configLocations;
    }

}
