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
//        List<Task> tasks1 = new ArrayList<>();
//        tasks1.add(new Task(true, "Task 1. Long long long long long long long long long description"));
//        tasks1.add(new Task(true, "Task 2"));
//        tasks1.add(new Task(false, "Task 3"));
//
//        List<Task> tasks2 = new ArrayList<>();
//        tasks2.add(new Task(true, "Task 4"));
//        tasks2.add(new Task(false, "Task 5"));
//
//        List<Task> tasks3 = new ArrayList<>();
//        tasks3.add(new Task(true, "Task 6"));
//        tasks3.add(new Task(false, "Task 7"));
//        tasks3.add(new Task(true, "Task 8"));
//        tasks3.add(new Task(true, "Task 9"));
//        tasks3.add(new Task(false, "Task 10"));
//
//        mCards.add(new Card("Category 1", tasks1));
//        mCards.add(new Card("Category 2", tasks2));
//        mCards.add(new Card("Category 3", tasks3));
    }

    public static void addCard(Card card) {
        mCards.add(card);
    }

    public static List<Card> getCards() {
        return mCards;
    }

    public static Card getCard(int index) {
        return mCards.get(index);
    }

    public static void removeCard(int index) {
        mCards.remove(index);
    }

    public static void changeCardTitle(int index, String title) {
        Card card = mCards.get(index);
        card.setTitle(title);
    }
}
