import store from "../Store";
import axios, {AxiosResponse} from "axios";
import {GatheringResourcesAcceptedActionFactory} from "../actions/gatheringresources/GatheringResourcesAcceptedAction";
import {GatheringResourcesDeclinedActionFactory} from "../actions/gatheringresources/GatheringResourcesDeclinedAction";

class GatheringResourcesCall {


    constructor () {

        store.subscribe((): void => {

            if(store.getState().gatheringResourceIssued) {
                axios.post(
                    "gameloop/gatheringResources",
                    {
                        "gameId": store.getState().gameId,
                        "playerId": store.getState().playerId,
                        "cardId": store.getState().resourcesCardDragged
                    },
                    {
                        headers: {
                            "Content-Type": "application/json; charset=utf-8"
                        }
                    },
                ).then((response: AxiosResponse): void => {
                    store.dispatch(GatheringResourcesAcceptedActionFactory.getInstance(response.data));
                }).catch((reason: string) => {
                    store.dispatch(GatheringResourcesDeclinedActionFactory.getInstance(reason));
                });
            }
        });
    }
}

export default GatheringResourcesCall;
