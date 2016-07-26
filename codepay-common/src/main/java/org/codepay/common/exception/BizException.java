package org.codepay.common.exception;
 

/**
* Description: BizException是业务逻辑处理异常，一般这类异常是不需要记录log，只是展示给页面显示并提示给用户。如你的用户名、密码为空等错误<br>
 * 
 * public String testAjax()
 *{
 *   try
 *    {
 *         genAjaxDataStr(0, "{}");
 *    } catch (BizException e)
 *    {
 *         getRequest().setAttribute(this.ERRORMESSAGE, e.getErrorMessage());
 *         return this.ERRORJSON;
 *    } catch (BizSystemException e)
 *    {
 *         getRequest().setAttribute(this.ERRORMESSAGE, this.SYSTEMERROR);
 *         return this.ERRORJSON;
 *    } catch (Exception e)
 *    {
 *         this.errorTrace("test", e.getMessage(), e);
 *         getRequest().setAttribute(this.ERRORMESSAGE, this.SYSTEMERROR);
 *         return this.ERRORJSON;
 *    }
 *    return this.NONE;
 *}
 * @date 2015-2-9
 */
public class BizException extends BizSystemException {
 
    private static final long serialVersionUID = 1L;
    
    public BizException(String msg) {
        super(msg);
    }
}

