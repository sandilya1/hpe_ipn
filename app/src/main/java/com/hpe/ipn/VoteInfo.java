package com.hpe.ipn;

/**
 * Created by ventrapr on 3/27/2017.
 */

public class VoteInfo {
    public String  uid;
    public String vote ;

    public VoteInfo(){

    }

    public VoteInfo(String uid, String vote) {
        this.uid = uid;
        this.vote = vote;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
