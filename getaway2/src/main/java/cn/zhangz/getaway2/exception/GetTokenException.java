package cn.zhangz.getaway2.exception;

public class GetTokenException extends RuntimeException{

    public GetTokenException(String msg)
    {
        super(msg);
    }
    public GetTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
