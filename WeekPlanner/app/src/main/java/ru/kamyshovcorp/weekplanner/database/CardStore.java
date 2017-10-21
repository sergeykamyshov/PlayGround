package ru.kamyshovcorp.weekplanner.database;

import java.util.ArrayList;
import java.util.List;

import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

/**
 * Временный класс для хранения карточек с задачами
 */
public class CardStore {

    private static CardStore mCardStore;

    private static List<Card> mCards = new ArrayList<>();

    public static CardStore getInstance() {
        if (mCardStore == null) {
            mCardStore = new CardStore();
            generateCards();
        }
        return mCardStore;
    }

    public static void generateCards() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(true, "Task 1. Long long long long long long long long long description"));
        tasks.add(new Task(true, "Task 2"));
        tasks.add(new Task(false, "Task 3"));

        mCards.add(new Card("Category 1", tasks));
        mCards.add(new Card("Category 2", tasks));
        mCards.add(new Card("Category 3", tasks));
        mCards.add(new Card("Category 4", tasks));
        mCards.add(new Card("Category 5", tasks));
    }

    public List<Card> getCards() {
        return mCards;
    }

    public void setCards(List<Card> cards) {
        mCards = cards;
    }
}
