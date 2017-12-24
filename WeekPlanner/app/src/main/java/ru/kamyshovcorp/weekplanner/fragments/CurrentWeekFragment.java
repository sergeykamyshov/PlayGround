package ru.kamyshovcorp.weekplanner.fragments;

import android.content.Intent;
import android.view.View;

import java.util.Date;

import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.utils.DateUtils;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_NEW_CARD_FLAG;

public class CurrentWeekFragment extends AbstractWeekFragment {

    public static CurrentWeekFragment newInstance() {
        return new CurrentWeekFragment();
    }

    @Override
    Date getWeekStartDate(Date date) {
        return DateUtils.getWeekStartDate(date);
    }

    @Override
    Date getWeekEndDate(Date date) {
        return DateUtils.getWeekEndDate(date);
    }

    @Override
    View.OnClickListener getOnFabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим к созданию новой карточки
                Intent intent = new Intent(getContext(), CardActivity.class);
                intent.putExtra(EXTRA_NEW_CARD_FLAG, true);
                getContext().startActivity(intent);
            }
        };
    }
}
