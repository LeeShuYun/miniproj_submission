package dev.leeshuyun.Lifeguild.models;

public class Weapon {
    String weaponid;
    String weaponname;
    String weapontype;
    int baseattack;
    double weaponfactor;
    int critrate;

    public String getWeaponid() {
        return weaponid;
    }

    public void setWeaponid(String weaponid) {
        this.weaponid = weaponid;
    }

    public String getWeaponname() {
        return weaponname;
    }

    public void setWeaponname(String weaponname) {
        this.weaponname = weaponname;
    }

    public String getWeapontype() {
        return weapontype;
    }

    public void setWeapontype(String weapontype) {
        this.weapontype = weapontype;
    }

    public int getBaseattack() {
        return baseattack;
    }

    public void setBaseattack(int baseattack) {
        this.baseattack = baseattack;
    }

    public double getWeaponfactor() {
        return weaponfactor;
    }

    public void setWeaponfactor(double weaponfactor) {
        this.weaponfactor = weaponfactor;
    }

    public int getCritrate() {
        return critrate;
    }

    public void setCritrate(int critrate) {
        this.critrate = critrate;
    }

}
