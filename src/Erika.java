public class Erika extends Trainer {

    public Erika() {
        desc = "Heal 50 damage from your active Pokemon.";
    }

    public void ability(Player user) {
        user.getActive().heal(50);
    }
}
