package com.gt.hardenedpwd.util;
public final class HPMath {
    /**
     * Return average value in array, NaN if no such value.
     */
    public static double mean(int[] a) {
        if (a.length == 0) return Double.NaN;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
        }
        return sum / a.length;
    }

     /**
     * Return sample variance of array, NaN if no such value.
     */
    public static double var(int[] a) {
        if (a.length == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }

        /**
     * Return sample standard deviation of array, NaN if no such value.
     */
    public static double stddev(int[] a) {
        return Math.sqrt(var(a));
    }

   }
