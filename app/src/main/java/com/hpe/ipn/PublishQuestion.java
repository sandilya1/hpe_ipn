package com.hpe.ipn;

/**
 * Created by ventrapr on 3/29/2017.
 */

public class PublishQuestion {

    String question_p;
    String option1,option2,option3,option4,publish_p;

    public PublishQuestion(){

    }

    public PublishQuestion(String question_p, String option1, String option2, String option3, String option4,String publish_p) {
        this.question_p = question_p;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.publish_p = publish_p;
    }

    public String getQuestion_p() {
        return question_p;
    }

    public void setQuestion_p(String question_p) {
        this.question_p = question_p;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getPublish_p(){return  publish_p;}

    public void setPublish_p(String publish_p){
        this.publish_p = publish_p;
    }
}
