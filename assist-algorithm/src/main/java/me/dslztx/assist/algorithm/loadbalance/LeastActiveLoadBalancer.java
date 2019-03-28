package me.dslztx.assist.algorithm.loadbalance;

import java.util.List;
import java.util.Random;

public class LeastActiveLoadBalancer<T extends ServiceProvider> extends AbstractLoadBalancer<T> {

    private final Random random = new Random();

    /**
     * @param serviceProviderList 列表大小大于1
     */
    @Override
    protected T doSelect(List<T> serviceProviderList) {
        int size = serviceProviderList.size();

        int leastActive = -1;
        int leastActiveCount = 0;
        int[] leastActiveIndexes = new int[size];

        int totalWeight = 0;
        int firstWeight = 0;
        boolean sameWeight = true;

        int active;
        int weight;
        for (int index = 0; index < size; index++) {
            active = serviceProviderList.get(index).getActive();
            weight = serviceProviderList.get(index).getWeight();

            if (leastActive == -1 || active < leastActive) {
                leastActive = active;

                leastActiveCount = 1;
                leastActiveIndexes[0] = index;

                firstWeight = weight;
                totalWeight = weight;

                sameWeight = true;
            } else if (active == leastActive) {
                leastActiveIndexes[leastActiveCount++] = index;

                totalWeight += weight;

                if (sameWeight && weight != firstWeight) {
                    sameWeight = false;
                }
            }
        }

        int leastActiveIndex = 0;

        if (leastActiveCount == 1) {
            leastActiveIndex = leastActiveIndexes[0];
            return serviceProviderList.get(leastActiveIndex);
        }

        if (!sameWeight) {
            // 当sameWeight=false的时候，totalWeight必大于0，因为单独weight>=0
            int probabilityWeight = random.nextInt(totalWeight);

            for (int index = 0; index < leastActiveCount; index++) {
                leastActiveIndex = leastActiveIndexes[index];

                probabilityWeight -= serviceProviderList.get(leastActiveIndex).getWeight();

                if (probabilityWeight <= 0) {
                    return serviceProviderList.get(leastActiveIndex);
                }
            }
        }

        leastActiveIndex = leastActiveIndexes[random.nextInt(leastActiveCount)];

        return serviceProviderList.get(leastActiveIndex);
    }
}
