package itz.next_step.android.data;

import itz.next_step.android.data.local.prefs.PreferencesService;
import itz.next_step.android.data.local.room.RoomService;
import itz.next_step.android.data.remote.ApiService;


public interface Repository {

    /**
     * ################################## Preference section ##################################
     */
    String getToken();
    void setToken(String token);

    PreferencesService getSharedPreferences();


    /**
     *  ################################## Remote api ##################################
     */
    ApiService getApiService();

    RoomService getRoomService();

}
