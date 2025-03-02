public class ProfessorsResearch extends Trainer {

    public ProfessorsResearch() {

        desc = "Discard your hand and draw 7 cards.";
    }

    public void ability(Player user) {
        user.getDiscard().addAll(user.getHand());
        user.getHand().clear();
        for (int i = 0; i < 7; i++) {
            user.drawCard();
        }
    }

    @Override
    public String toString() {
        return "Professors Research";
    }
}
