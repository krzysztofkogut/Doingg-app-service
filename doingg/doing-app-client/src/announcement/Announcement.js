import React, { Component } from 'react';
import './Announcement.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Announcement extends Component {
    calculatePercentage = (choice) => {
        if(this.props.offer.totalVotes === 0) {
            return 0;
        }
        return (choice.voteCount*100)/(this.props.offer.totalVotes);
    };

    isSelected = (choice) => {
        return this.props.offer.selectedChoice === choice.id;
    }

    getWinningChoice = () => {
        return this.props.offer.choices.reduce((prevChoice, currentChoice) => 
            currentChoice.voteCount > prevChoice.voteCount ? currentChoice : prevChoice, 
            {voteCount: -Infinity}
        );
    }

    getTimeRemaining = (offer) => {
        const expirationTime = new Date(offer.expirationDateTime).getTime();
        const currentTime = new Date().getTime();
    
        var difference_ms = expirationTime - currentTime;
        var seconds = Math.floor( (difference_ms/1000) % 60 );
        var minutes = Math.floor( (difference_ms/1000/60) % 60 );
        var hours = Math.floor( (difference_ms/(1000*60*60)) % 24 );
        var days = Math.floor( difference_ms/(1000*60*60*24) );
    
        let timeRemaining;
    
        if(days > 0) {
            timeRemaining = days + " days left";
        } else if (hours > 0) {
            timeRemaining = hours + " hours left";
        } else if (minutes > 0) {
            timeRemaining = minutes + " minutes left";
        } else if(seconds > 0) {
            timeRemaining = seconds + " seconds left";
        } else {
            timeRemaining = "less than a second left";
        }
        
        return timeRemaining;
    }

    render() {       
        return (
            <div className="offer-container">
            <div className="offer-content">
                <div className="offer-header">
                    <div className="offer-creator-info">
                        <Link className="creator-link" to={`/users/${this.props.offer.createdBy.username}`}>
                            <Avatar className="offer-creator-avatar" 
                                style={{ backgroundColor: getAvatarColor(this.props.offer.createdBy.name)}} >
                                {this.props.offer.createdBy.name[0].toUpperCase()}
                            </Avatar>
                            <span className="offer-creator-name">
                                {this.props.offer.createdBy.name}
                            </span>
                            <span className="offer-creator-username">
                                @{this.props.offer.createdBy.username}
                            </span>
                            <span className="offer-creation-date">
                                {formatDateTime(this.props.offer.creationDateTime)}
                            </span>
                        </Link>
                    </div>
                    <div className="offer-question">
                       {this.props.offer.question}
                    </div>
                    <div className="offer-choices">
                       City: {this.props.offer.city}
                    </div>
                    <div className="offer-choices">
                       Number: {this.props.offer.phone}
                    </div>
                    <div className="offer-choices">
                       Wage: {this.props.offer.wage}
                    </div>
                </div>
                <div className="offer-choices">
                    <RadioGroup 
                        className="offer-choice-radio-group" 
                        onChange={this.props.handleVoteChange} 
                        value={this.props.currentVote}>
                    </RadioGroup>
                </div>
                <div className="offer-footer">
                    { 
                        !(this.props.offer.expired)?
                        (<Button className="vote-button" disabled={this.props.offer.expired} onClick={this.props.handleVoteSubmit}>I am interested </Button>) : null 
                    }
                    <span className="total-votes">{this.props.offer.totalVotes} interested</span>
                    <span className="separator">•</span>
                    <span className="time-left">
                        {
                            this.props.offer.expired ? "Expired" :
                            this.getTimeRemaining(this.props.offer)
                        }
                    </span>
                </div>
            </div>
        </div>

        );
    }
}




export default Announcement;
