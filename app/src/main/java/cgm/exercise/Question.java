package cgm.exercise;

import java.util.LinkedList;
import java.util.stream.IntStream;

public class Question {
    private String question;
    private String[] answers;

    private static String[] parseAnswers(String rawAnswers) {
        var answers = new LinkedList<String>();
        
        var chars = rawAnswers.chars().toArray();

        for (var i = 0; i < chars.length; i++) {
            char c = (char) chars[i];

            if (c == '"') {
                var escaped = false;
                var answer = "";

                i++;

                while(i < chars.length) {
                    c = (char) chars[i];

                    if (!escaped && c == '"') {
                        answers.add(answer);
                        break;
                    } else if (!escaped && c == '\\') {
                        escaped = true;
                    } else {
                        answer += c;
                        escaped = false;
                    }

                    i++;
                }
            } else if (c != ' ') {
                throw new Error("Invalid answer format. ((\"<answer>\")+)");
            }
        }

        return answers.toArray(new String[0]);
    }

    public Question(String question, String[] answers) {
        this.question = question;
        this.answers = answers;
    }

    public static Question parse(String rawQuestion) {
        if (rawQuestion.length() > 255) {
            throw new Error("A question has to be 255 or less characters");
        }

        var tokens = rawQuestion.trim().split("\\?", 2);

        if (tokens.length < 2 || tokens[1].isBlank()) {
            throw new Error("Invalid question format. (<question>? (\"<answer>\")+)");
        }

        return new Question(tokens[0], parseAnswers(tokens[1]));
    }

    public String getQuestion() {
        return this.question;
    }

    public String[] getAnswers() {
        return this.answers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Question)) {
            return false;
        }

        Question other = (Question) o;

        if (this.answers.length != other.answers.length) {
            return false;
        }

        var answersEqual = IntStream
            .range(0, this.answers.length)
            .mapToObj(idx -> this.answers[idx].equals(other.answers[idx]))
            .allMatch(x -> x);

        return answersEqual && this.question.equals(other.question);
    }
}
