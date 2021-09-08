package com.digilibrary.Logger;

//Singleton logger mostly because writing System.out.println is annoying to me.
//In reality could implement different types of loggers, so it's interesting enough to include
//just prints logging
public class BasicLogger implements ILogger {

    @Override
    public void LogInfo(String info) {
        System.out.println(info);
    }

    @Override
    public void LogException(Exception e, String extraInfo) {
        e.printStackTrace();
        if (extraInfo != null){
            System.out.println(extraInfo);
        }
    }
}
