package cgm.exercise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test void requiresAnAnswer() {
        assertThrows(Error.class, () -> {
            Question.parse("Does this work?");
        });
    }

    @Test void allowsMultipleQuestionMarks() {
        assertDoesNotThrow(() -> {
            Question.parse("Does this work? \"yes?\"");
        });
    }

    @Test void acceptsMultipleAnswers() {
        assertDoesNotThrow(() -> {
            Question.parse("Does this work? \"yes\" \"yes2\"");
        });
    }

    @Test void checksStringSize() {
        assertDoesNotThrow(() -> {
            Question.parse("Does this work? \"yes\"");
        });

        assertThrows(Error.class, () -> {
            Question.parse("Does this work? \"no\"" + "x".repeat(256));
        });
    }

    @Test void correctlyParsesSingleAnswerQuestion() {
        var subject = Question.parse("Does this work? \"yes\"");
        var expected = new Question("Does this work", new String[] { "yes" });

        assertEquals(subject, expected);
    }

    @Test void correctlyParsesMultipleAnswerQuestion() {
        var subject = Question.parse("Does this work? \"yes\" \"yes2\"");
        var expected = new Question("Does this work", new String[] { "yes", "yes2" });

        assertEquals(subject, expected);
    }
}
