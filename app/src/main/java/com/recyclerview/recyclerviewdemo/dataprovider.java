package com.recyclerview.recyclerviewdemo;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Naman Khurpia on 14-02-2018.
 */

public class dataprovider {


    public dataprovider(int img_res,String f_name,String d_name){
        this.setImg_res(img_res);
        this.setF_name(f_name);
        this.setD_name(d_name);

    }


    private int img_res;
    private String f_name,d_name;

    public int getImg_res() {
        return img_res;
    }

    public void setImg_res(int img_res) {
        this.img_res = img_res;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }
}
