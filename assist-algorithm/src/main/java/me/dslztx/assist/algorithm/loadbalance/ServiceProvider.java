package me.dslztx.assist.algorithm.loadbalance;

public interface ServiceProvider {

    /**
     * @return 必须大于等于0
     */
    int getActive();

    /**
     * @return 必须大于等于0
     */
    int getWeight();
}
