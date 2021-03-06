import React from "react";
import {getChosenPlayer} from "../storeutil";
import Card from "./Card";

class Spot extends React.Component {

    constructor(props) {
        super(props);
        this.handleDrop = this.handleDrop.bind(this);
    }

    handleDrop(event) {
        event.preventDefault();
        switch(event.dataTransfer.getData("type")) {
            case "card":
                this.props.store.dispatch({
                    type: "PLAY_CARD_ON_SPOT_REQUESTED",
                    cardId: event.dataTransfer.getData("payload").id,
                    ownerId: getChosenPlayer(this.props.store).id,
                    spotId: this.props.id
                });
                break;
            default:
                console.log("Something weird was dropped on spot: " + event);
        }
    }

    render() {
        const chosenPlayer = getChosenPlayer(this.props.store);

        if(chosenPlayer) {

            const style = {
                width: "100%",
                height: "100%",
                background: "lightgray"
            };

            const spot = chosenPlayer.spots
                .filter((spot) => spot.id === this.props.id);

            return <div className="Spot"
                        onDrop={this.handleDrop}
                        onDragOver={(event) => event.preventDefault()}
                        style={style}>
                {spot && spot.creature &&
                <Card key={spot.creature.id}
                      card={spot.creature}
                      store={this.props.store}/>}
            </div>;
        } else {
            return <div/>;
        }
    }

}

export default Spot;
