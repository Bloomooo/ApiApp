package com.api.projet.inter;

import com.api.projet.adapter.GameAdapter;

public interface PlayerInterfaction {

    void setPoint(int point);

    void setBackgroundColor(int color);

    void enableAnswerViewText(String answer);

    void disableAnswerViewText();
}
