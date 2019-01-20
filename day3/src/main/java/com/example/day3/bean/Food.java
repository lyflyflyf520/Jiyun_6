package com.example.day3.bean;

import java.util.List;

public class Food {


    /**
     * ret : 1
     * data : [{"id":"10509","title":"水煮肉片","pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/11/10509.jpg","collect_num":"1339","food_str":"瘦猪肉 生菜 豆瓣酱 干辣椒 花椒","num":1339},{"id":"46968","title":"红糖苹果银耳汤","pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/47/46968.jpg","collect_num":"1252","food_str":"银耳 苹果 红糖","num":1252},{"id":"10191","title":"麻婆豆腐","pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/11/10191.jpg","collect_num":"1218","food_str":"豆腐 肉末 生抽 白糖 芝麻油","num":1218},{"id":"2372","title":"皮蛋瘦肉粥","pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/3/2372.jpg","collect_num":"1151","food_str":"大米 皮蛋 猪肉 油条 香葱","num":1151},{"id":"2166","title":"蚂蚁上树","pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/3/2166.jpg","collect_num":"1143","food_str":"红薯粉 肉 姜 蒜 花椒","num":1143}]
     */

    private int ret;
    private List<DataBean> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10509
         * title : 水煮肉片
         * pic : http://www.qubaobei.com/ios/cf/uploadfile/132/11/10509.jpg
         * collect_num : 1339
         * food_str : 瘦猪肉 生菜 豆瓣酱 干辣椒 花椒
         * num : 1339
         */

        private String id;
        private String title;
        private String pic;
        private String collect_num;
        private String food_str;
        private int num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(String collect_num) {
            this.collect_num = collect_num;
        }

        public String getFood_str() {
            return food_str;
        }

        public void setFood_str(String food_str) {
            this.food_str = food_str;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
