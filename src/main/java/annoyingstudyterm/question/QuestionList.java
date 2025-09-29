package annoyingstudyterm.question;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class QuestionList {
    private List<Question> list;

    public QuestionList(List<Question> list) {
        this.list = list;
    }

    public int length() {
        return this.list.size();
    }
    public List<Question> getAll() {
        return this.list;
    }
    public Question getRandom() {
        Random random = new Random();
        return this.list.get(random.nextInt(this.list.size()));
    }
    public List<Question> getRandomN(int amount) {
        if (this.list.size() < amount) {
            amount = this.list.size();
        }
        Set<Integer> indexes = new HashSet<>(amount);
        List<Question> questions = new ArrayList<>(amount);

        for (var i = 0; i < amount; i++) {
            Integer luckyN = 0;
            while (indexes.contains(luckyN)) {
                luckyN = new Random().nextInt(this.list.size());
            }
            questions.add(this.list.get(luckyN));
        }
        return questions;
    }
}
