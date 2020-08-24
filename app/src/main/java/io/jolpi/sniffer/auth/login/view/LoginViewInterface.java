package io.jolpi.sniffer.auth.login.view;

/**
 * Created by nasser on 4/2/18.
 */

public interface LoginViewInterface {
    /**
     * Show progress bar.
     */

    void showProgress();

    /**
     * Hide progress bar
     */

    void hideProgress(String message);

    /**
     * Error when parsing from server.
     */

    void setParsingError();

    void showNextPage();
}
