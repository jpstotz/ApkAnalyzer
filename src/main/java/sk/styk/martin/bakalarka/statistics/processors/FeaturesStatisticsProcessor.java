package sk.styk.martin.bakalarka.statistics.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.styk.martin.bakalarka.analyze.data.AndroidManifestData;
import sk.styk.martin.bakalarka.analyze.data.ApkData;
import sk.styk.martin.bakalarka.statistics.data.FeaturesStatistics;
import sk.styk.martin.bakalarka.statistics.processors.helpers.PercentageHelper;
import sk.styk.martin.bakalarka.statistics.processors.helpers.SortingHelper;
import sk.styk.martin.bakalarka.utils.data.MathStatistics;
import sk.styk.martin.bakalarka.utils.data.PercentagePair;
import sk.styk.martin.bakalarka.utils.files.JsonUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin Styk on 21.01.2016.
 */
public class FeaturesStatisticsProcessor {
    private List<File> jsons;
    private FeaturesStatistics featuresStatistics;
    private static final Logger logger = LoggerFactory.getLogger(FeaturesStatisticsProcessor.class);


    public FeaturesStatisticsProcessor(List<File> jsons) {
        if (jsons == null || jsons.isEmpty())
            throw new IllegalArgumentException("jsons");

        this.jsons = jsons;
    }

    public static FeaturesStatisticsProcessor ofFiles(List<File> jsons) {
        return new FeaturesStatisticsProcessor(jsons);
    }

    public FeaturesStatistics process() {

        featuresStatistics = new FeaturesStatistics();
        Map<String, Integer> topFeatures = new HashMap<String, Integer>();
        List<Double> featuresNumbersList = new ArrayList<Double>();
        List<Double> featuresNumbersListNonZero = new ArrayList<Double>();
        int manifestFound = 0;

        for (int i = 0; i < jsons.size(); i++) {
            if (i % StatisticsProcessor.PRINT_MESSAGE_INTERVAL == 0) {
                logger.info("Loading json number " + i);
            }

            File f = jsons.get(i);
            ApkData data = JsonUtils.fromJson(f);
            AndroidManifestData manifestData = null;


            if (data != null && data.getAndroidManifest() != null) {

                manifestFound++;
                manifestData = data.getAndroidManifest();

                List<String> featuresList = manifestData.getUsesFeature();

                if (featuresList != null) {
                    int listSize = featuresList.size();
                    featuresNumbersList.add(new Double(listSize));
                    if (listSize != 0) {
                        featuresNumbersListNonZero.add(new Double(listSize));
                    }

                    for (String perm : featuresList) {
                        if (topFeatures.containsKey(perm)) {
                            Integer oldValue = topFeatures.get(perm);
                            topFeatures.put(perm, ++oldValue);
                        } else {
                            topFeatures.put(perm, 1);
                        }
                    }

                }

            }
        }


        featuresStatistics.setAnalyzedApks(manifestFound);
        setValues(manifestFound, featuresNumbersList, false);
        setValues(manifestFound, featuresNumbersListNonZero, true);
        setTopFeatures(topFeatures, featuresNumbersList.size());

        return featuresStatistics;
    }

    private void setValues(int manifestFound, List<Double> featuresNumbersList, boolean isNonZero) {

        if (featuresNumbersList == null) {
            throw new NullPointerException("featuresNumbersList null");
        }

        logger.info("Started processing features");

        MathStatistics mathStatistics = new MathStatistics(new PercentagePair(featuresNumbersList.size(), manifestFound), featuresNumbersList);

        if (isNonZero) {
            featuresStatistics.setFeaturesNonZero(mathStatistics);
        } else {
            featuresStatistics.setFeatures(mathStatistics);
        }

        logger.info("Finished processing features");
    }

    private void setTopFeatures(Map<String, Integer> topFeatures, Integer wholeData) {

        logger.info("Started processing features chart");

        if (wholeData == null) {
            throw new IllegalArgumentException("wholeData");
        }
        if (topFeatures == null) {
            throw new IllegalArgumentException("topFeatures");
        }

        Map<String, PercentagePair> result = new HashMap<String, PercentagePair>();

        for (Map.Entry<String, Integer> entry : topFeatures.entrySet()) {
            String name = entry.getKey();
            Integer number = entry.getValue();
            BigDecimal percentage = PercentageHelper.getPercentage(number.doubleValue(), wholeData);

            PercentagePair percentageEntry = new PercentagePair(number, percentage);

            result.put(name, percentageEntry);
        }
        featuresStatistics.setTopFeatures(SortingHelper.sortByValue(result));

        logger.info("Finished processing features chart");
    }

}
