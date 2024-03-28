package com.api.projet.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for the slideshow section of the application.
 */
public class SlideshowViewModel extends ViewModel {

    /** LiveData object to hold the text displayed in the slideshow fragment. */
    private final MutableLiveData<String> mText;

    /**
     * Constructor for the SlideshowViewModel class.
     * Initializes the MutableLiveData object with a default text value.
     */
    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    /**
     * Retrieves the LiveData object containing the text to be displayed in the slideshow fragment.
     * @return LiveData object containing the text.
     */
    public LiveData<String> getText() {
        return mText;
    }
}
