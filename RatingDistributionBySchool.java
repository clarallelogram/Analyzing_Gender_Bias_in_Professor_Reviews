package finalproject;

import java.util.ArrayList;

public class RatingDistributionBySchool extends DataAnalyzer {
    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTable;
    public RatingDistributionBySchool(Parser p) {
        super(p);
    }

    @Override
    public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
        // ADD YOUR CODE BELOW THIS
        MyHashTable<String, Integer> profAndAvgToCount = this.nestedHashTable.get(keyword.trim().toLowerCase());
        if (profAndAvgToCount == null) {
            return null;
        }
        for (String key : profAndAvgToCount.getKeySet()) {
            String[] profAndAvg = key.split("\\n");
            Integer value = profAndAvgToCount.get(key);
            String roundedAvg = String.valueOf((double) Math.round(Double.parseDouble(profAndAvg[1]) * 100) / 100);
            profAndAvgToCount.remove(key);
            profAndAvgToCount.put(profAndAvg[0]+"\n"+roundedAvg, value);
        }
        return this.nestedHashTable.get(keyword.trim().toLowerCase());
    }
        //ADD YOUR CODE ABOVE THIS

    @Override
    public void extractInformation() {
        // ADD YOUR CODE BELOW THIS
        RatingDistributionByProf ratingDistByProf = new RatingDistributionByProf(parser);
        MyHashTable<String, Integer> innerHashTable;
        MyHashTable<String, String> profToAvg = new MyHashTable<>();
        nestedHashTable = new MyHashTable<String, MyHashTable<String, Integer>>();

        for (String[] line : parser.data) {
            double avg = 0;
            String avgRating = "";
            Integer totalReviewCount = 0;
            String profName = line[parser.fields.get("professor_name")].trim().toLowerCase();
            String school = line[parser.fields.get("school_name")].trim().toLowerCase();
            double rating = Double.parseDouble(line[parser.fields.get("student_star")]);

            totalReviewCount = ratingDistByProf.getDistByKeyword(profName).get("1")+ratingDistByProf.getDistByKeyword(profName).get("2")+ratingDistByProf.getDistByKeyword(profName).get("3")+ratingDistByProf.getDistByKeyword(profName).get("4")+ratingDistByProf.getDistByKeyword(profName).get("5");
            avg = rating / totalReviewCount;

            innerHashTable = nestedHashTable.get(school);

            if (innerHashTable == null) {
                innerHashTable = new MyHashTable<String, Integer>();
                avgRating = String.valueOf(avg);
                innerHashTable.put(profName+"\n"+avgRating, totalReviewCount);
                nestedHashTable.put(school, innerHashTable);
                profToAvg.put(profName, avgRating);
            }
            else if (profToAvg.get(profName)!=null){
                double newAvg = (Double.parseDouble(profToAvg.get(profName))+avg);
                innerHashTable.remove(profName+"\n"+profToAvg.get(profName));
                innerHashTable.put(profName+"\n"+newAvg, totalReviewCount);
                profToAvg.put(profName, String.valueOf(newAvg));
            }
            else if (profToAvg.get(profName)==null) {
                avgRating = String.valueOf(avg);
                innerHashTable.put(profName+"\n"+avgRating, totalReviewCount);
                profToAvg.put(profName, avgRating);
            }
            else {
                avgRating = String.valueOf(avg);
                innerHashTable.put(profName+"\n"+avgRating, totalReviewCount);
                profToAvg.put(profName, avgRating);
            }
            //ADD YOUR CODE ABOVE THIS
        }}}
