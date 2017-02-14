package com.shuan.Project.list;


public class Sample {
    String dis, insName, board, loc;
    String u_id, proPic, name, pos, companyName, level;
    String cty, distrct, state, contry;
    String orgName, type, addr, land, pin;
    String jId, jTitle, jSkill, jLevel, jLoc, jCreate, jView, jApply, jShare, jFrmId, jImp,fp,fs,fl;
    String id,frmId,toId,postId,content,vwed;

    public Sample(String pro_pic, String name, String venue, String intervew_date, String intervew_time, String type, String uid) {
    }


    public Sample(String u_id, String name, String level) {
        this.u_id = u_id;
        this.name = name;
        this.level = level;
    }

    public Sample(String dis, String insName, String board, String loc) {
        this.dis = dis;
        this.insName = insName;
        this.board = board;
        this.loc = loc;
    }


    public Sample(String u_id, String proPic, String name, String level, String pos, String companyName) {
        this.u_id = u_id;
        this.proPic = proPic;
        this.name = name;
        this.level = level;
        this.pos = pos;
        this.companyName = companyName;
    }

    public Sample(String name) {
        this.name = name;
    }

    public Sample(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public Sample(String dis, String cty, String distrct, String state, String contry) {
        this.dis = dis;
        this.cty = cty;
        this.distrct = distrct;
        this.state = state;
        this.contry = contry;
    }


    public Sample(String dis, String orgName, String type, String addr, String land, String contry, String state, String cty, String pin, String distrct) {
        this.dis = dis;
        this.orgName = orgName;
        this.type = type;
        this.addr = addr;
        this.land = land;
        this.contry = contry;
        this.state = state;
        this.cty = cty;
        this.pin = pin;
        this.distrct = distrct;
    }

    public Sample(String id, String frmId, String toId, String postId, String content, String type, String vwed,String level) {
        this.id = id;
        this.frmId = frmId;
        this.toId = toId;
        this.postId = postId;
        this.content = content;
        this.type = type;
        this.vwed = vwed;
        this.level=level;
    }

    public Sample(String companyName, String proPic, String jId, String jTitle, String jSkill, String jLevel, String jLoc, String jCreate, String jView, String jApply, String jShare, String jFrmId, String jImp,String fp,String fs,String fl) {
        this.companyName = companyName;
        this.proPic = proPic;
        this.jId = jId;
        this.jTitle = jTitle;
        this.jSkill = jSkill;
        this.jLevel = jLevel;
        this.jLoc = jLoc;
        this.jCreate = jCreate;
        this.jView = jView;
        this.jApply = jApply;
        this.jShare = jShare;
        this.jFrmId = jFrmId;
        this.jImp = jImp;
        this.fp=fp;
        this.fs=fs;
        this.fl=fl;
    }



    public Sample(String companyName, String proPic, String jId, String jTitle, String jSkill, String jLevel, String jLoc, String jCreate, String jView, String jApply, String jShare) {
        this.companyName = companyName;
        this.proPic = proPic;
        this.jId = jId;
        this.jTitle = jTitle;
        this.jSkill = jSkill;
        this.jLevel = jLevel;
        this.jLoc = jLoc;
        this.jCreate = jCreate;
        this.jView = jView;
        this.jApply = jApply;
        this.jShare = jShare;


    }



    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getCty() {
        return cty;
    }

    public void setCty(String cty) {
        this.cty = cty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getjId() {
        return jId;
    }

    public void setjId(String jId) {
        this.jId = jId;
    }

    public String getjTitle() {
        return jTitle;
    }

    public void setjTitle(String jTitle) {
        this.jTitle = jTitle;
    }

    public String getjSkill() {
        return jSkill;
    }

    public void setjSkill(String jSkill) {
        this.jSkill = jSkill;
    }

    public String getjLevel() {
        return jLevel;
    }

    public void setjLevel(String jLevel) {
        this.jLevel = jLevel;
    }

    public String getjLoc() {
        return jLoc;
    }

    public void setjLoc(String jLoc) {
        this.jLoc = jLoc;
    }

    public String getjCreate() {
        return jCreate;
    }

    public void setjCreate(String jCreate) {
        this.jCreate = jCreate;
    }

    public String getjView() {
        return jView;
    }

    public void setjView(String jView) {
        this.jView = jView;
    }

    public String getjApply() {
        return jApply;
    }

    public void setjApply(String jApply) {
        this.jApply = jApply;
    }

    public String getjShare() {
        return jShare;
    }

    public void setjShare(String jShare) {
        this.jShare = jShare;
    }

    public String getjFrmId() {
        return jFrmId;
    }

    public void setjFrmId(String jFrmId) {
        this.jFrmId = jFrmId;
    }


    public String getjImp() {
        return jImp;
    }

    public void setjImp(String jImp) {
        this.jImp = jImp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrmId() {
        return frmId;
    }

    public void setFrmId(String frmId) {
        this.frmId = frmId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVwed() {
        return vwed;
    }

    public void setVwed(String vwed) {
        this.vwed = vwed;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }
}
