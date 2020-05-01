package com.prehax.gomovie;

class Coupon{

        private String couponName;

        public void setCouponDiscount(double couponDiscount) {
                this.couponDiscount = couponDiscount;
        }

        private int couponId;
        private double couponDiscount;

        public String getcouponName(){ return couponName; }
        public void setCouponName(String CouponName){this.couponName=couponName;}
        public int getcouponId(){return couponId;}
        public void setcouponId(int couponId){this.couponId=couponId;}
        public double getcouponDiscount(){return couponDiscount;}
        public void setCouponDiscount(){this.couponDiscount=couponDiscount;}

}