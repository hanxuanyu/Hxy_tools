package com.hxuanyu.mytools.beans;

public class PhoneInfoBean {

    /**
     * ret : 200
     * msg : success
     * log_id : 62D7A76F-B827-40A6-AB4E-640254D4F992
     * data : {"types":"中国移动 GSM","lng":"113.264434","city":"广州","num":1591743,"isp":"移动","area_code":"440100","city_code":"020","prov":"广东","zip_code":"510000","lat":"23.129162"}
     */

    private int ret;
    private String msg;
    private String log_id;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * types : 中国移动 GSM
         * lng : 113.264434
         * city : 广州
         * num : 1591743
         * isp : 移动
         * area_code : 440100
         * city_code : 020
         * prov : 广东
         * zip_code : 510000
         * lat : 23.129162
         */

        private String types;
        private String lng;
        private String city;
        private int num;
        private String isp;
        private String area_code;
        private String city_code;
        private String prov;
        private String zip_code;
        private String lat;

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
