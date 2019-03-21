package com.sandu.common.util;

public class ModelTable {

    /**
     * eg: fileName -> file_name
     *
     * @param field
     * @return
     */
    public static String toClumn(String bean) {
        if (bean == null || bean.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bean.length(); i++) {
            char c = bean.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /*
     * order by beanName Desc,test asc
     */
    public static String toOrders(String order) {
        if (order == null || order.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        String[] orders = order.split(",");
        for (String s : orders) {
            if (s.indexOf(" DESC") != -1 || s.indexOf(" desc") != -1
                    || s.indexOf(" Desc") != -1) {
                sb.append(" "
                        + toClumn(s.replace(" DESC", "").replace(" desc", "")
                        .replace(" Desc", "").trim()) + " desc,");
            }
            if (s.indexOf(" ASC") != -1 || s.indexOf(" asc") != -1
                    || s.indexOf(" Asc") != -1) {
                sb.append(" "
                        + toClumn(s.replace(" ASC", "").replace(" asc", "")
                        .replace(" Asc", "").trim()) + " asc,");
            }
        }

        return sb.toString().substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {
        String test = "beanName Desc,test asc,tabDesc desc";
        //////System.out.println("test2=" + test.indexOf(" asc"));
        //////System.out.println("test=" + toOrders(test));
    }

}
