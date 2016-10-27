package com.shuan.project.list;

/**
 * Created by Android on 7/14/2016.
 */
public class ResumeList {
    String ins_name, board, concentration, location, aggregate;
    String cerName, cerCentre;
    String aName;
    String title, platform, role, team_mem, dur,desc,stus;
    String orgName,pos,loc,frm_dat,to_dat;

    public ResumeList() {
    }


    public ResumeList(String ins_name, String board, String concentration, String location, String aggregate) {
        this.ins_name = ins_name;
        this.board = board;
        this.concentration = concentration;
        this.location = location;
        this.aggregate = aggregate;
    }

    public ResumeList(String cerName, String cerCentre) {
        this.cerName = cerName;
        this.cerCentre = cerCentre;
    }

    public ResumeList(String cerName, String cerCentre, String dur) {
        this.cerName = cerName;
        this.cerCentre = cerCentre;
        this.dur = dur;
    }

    public ResumeList(String aName) {
        this.aName = aName;
    }

    public ResumeList(String title, String platform, String role, String team_mem, String dur,String desc,String stus) {
        this.title = title;
        this.platform = platform;
        this.role = role;
        this.team_mem = team_mem;
        this.dur=dur;
        this.desc = desc;
        this.stus=stus;
    }

    public ResumeList(String orgName, String pos, String loc, String frm_dat, String to_dat,String stus) {
        this.orgName = orgName;
        this.pos = pos;
        this.loc = loc;
        this.frm_dat = frm_dat;
        this.to_dat = to_dat;
        this.stus=stus;
    }


    public String getIns_name() {
        return ins_name;
    }

    public void setIns_name(String ins_name) {
        this.ins_name = ins_name;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public String getCerName() {
        return cerName;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public String getCerCentre() {
        return cerCentre;
    }

    public void setCerCentre(String cerCentre) {
        this.cerCentre = cerCentre;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam_mem() {
        return team_mem;
    }

    public void setTeam_mem(String team_mem) {
        this.team_mem = team_mem;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFrm_dat() {
        return frm_dat;
    }

    public void setFrm_dat(String frm_dat) {
        this.frm_dat = frm_dat;
    }

    public String getTo_dat() {
        return to_dat;
    }

    public void setTo_dat(String to_dat) {
        this.to_dat = to_dat;
    }


}
