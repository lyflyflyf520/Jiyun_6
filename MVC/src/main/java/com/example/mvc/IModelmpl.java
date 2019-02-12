package com.example.mvc;

import java.util.Random;

public class IModelmpl implements IModel{


    @Override
    public void updateUI(IUpdateListener iUpdateListener) {

        Random random = new Random();
        int result  = random.nextInt(100);

        if (result%2==0){
            iUpdateListener.updateUI("成功="+result);
        }else{
            iUpdateListener.updateUIFailed("失败="+result);
        }
    }
}
