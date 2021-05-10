import java.util.LinkedList;
import java.util.List;

public enum TripStates {
    TRIPCREATED,
    DRIVERASSSIGNED,
    DRIVERREACHED,
    TRIPSTARTED,
    TRIPENDED;

    private List<TripStates> validFromStates = new LinkedList<TripStates>();


    TripStates(List<TripStates> validFromStates) {
        this.validFromStates = validFromStates;
    }

    TripStates() {
    }

    public boolean canChangeTripState(TripStates fromState, TripStates toState){
        return fromState.ordinal()<toState.ordinal();
    }

}
