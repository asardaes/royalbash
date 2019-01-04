import React from "react";

class Card extends React.Component {

    constructor(props) {
        super(props);
        this.handleDragStart = this.handleDragStart.bind(this);
    }

    handleDragStart(event) {
        event.dataTransfer.setData("type", "card");
        event.dataTransfer.setData("payload", this.props.card);
        console.log(this.props.card.name + " is being dragged");
    }

    render() {

        let card = this.props.card;

        let cardStyle = {
            height: "100%",
            width: "100%"
        };

        return (
            <div className="Card"
                 draggable={this.props.store.getState().onTurn}
                 style={cardStyle}
                 onDragStart={this.handleDragStart}>
                <div className="name">{card.name}</div>
                <div className="text">{card.text}</div>
                <div className="cost">{card.cost}</div>
                <div className="image"><img src={card.image} alt={card.name}/></div>
            </div>
        );
    }
}

export default Card;