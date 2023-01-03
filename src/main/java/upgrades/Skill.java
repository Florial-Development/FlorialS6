package upgrades;

public enum Skill {
    BURROW(1, 0),
    SCENT(2, 0),
    RESISTANCE(3, 0),
    STRENGTH(4, 0),
    TBA(5, 0);

    public final int skill;
    public int lvl;
    Skill(int skill, int lvl) {

        this.skill = skill;
        this.lvl = lvl;
    }

    public static Skill getSkillsId(int skill) {
        for (Skill e : values()) {

            if (e.skill == skill) return e;
        }
        return null;
    }

    public int getSkillLevel() {
        return lvl;
    }

    public void setLevel(int level) {
        this.lvl = level;
    }
}
