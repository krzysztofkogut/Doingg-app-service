import React, { Component } from 'react';
import { getAllOffers, getUserCreatedOffers, getUserVotedOffers } from '../util/APIUtils';
import Offer from './Announcement';
import { castVote } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Button, Icon, notification } from 'antd';
import { OFFER_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';
import './AnnouncementList.css';
import searchfield from '../app/SearchBox';
import SearchBox from '../app/SearchBox';



class AnnouncementList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            offers: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false,
            searchfield : ''

        };
        this.loadOfferList = this.loadOfferList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }
    loadOfferList(page = 0, size = OFFER_LIST_SIZE) {
        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATED_OFFERS') {
                promise = getUserCreatedOffers(this.props.username, page, size);
            } else if (this.props.type === 'USER_VOTED_OFFERS') {
                promise = getUserVotedOffers(this.props.username, page, size);                               
            }
        } else {
            promise = getAllOffers(page, size);
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise            
        .then(response => {
            const offers = this.state.offers.slice();
            const currentVotes = this.state.currentVotes.slice();

            this.setState({ 
                offers: offers.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                currentVotes: currentVotes.concat(Array(response.content.length).fill(null)),
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });  
        
    }

    componentDidMount() {
        this.loadOfferList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                offers: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });    
            this.loadOfferList();
        }
    }

    handleLoadMore() {
        this.loadOfferList(this.state.page + 1);
    }

    handleVoteSubmit(event, offerIndex) {
        event.preventDefault();
        if(!this.props.isAuthenticated) {
            this.props.history.push("/login");
            notification.info({
                message: 'Doingg App',
                description: "Please login to sign as interested.",          
            });
            return;
        }

        const offer = this.state.offers[offerIndex];

        const voteData = {
            offerId: offer.id
        };

        castVote(voteData)
        .then(response => {
            const offers = this.state.offers.slice();
            offers[offerIndex] = response;
            this.setState({
                offers: offers
            });        
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login to take an offer');    
            } else {
                notification.error({
                    message: 'Doingg App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });                
            }
        });
    }

    searchText = (e) => {
		return this.setState({ searchfield: e.target.value });
	};


    render() {
        const offerViews = [];
        this.state.offers.forEach((offer, offerIndex) => {
            if(offer.question.toLowerCase().includes(this.state.searchfield.toLowerCase())){
            offerViews.push(<Offer 
                key={offer.id} 
                offer={offer}
                currentVote={this.state.currentVotes[offerIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, offerIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, offerIndex)} />)  
                }
            else if(offer.city.toLowerCase().includes(this.state.searchfield.toLowerCase())){
                offerViews.push(<Offer 
                key={offer.id} 
                offer={offer}
                currentVote={this.state.currentVotes[offerIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, offerIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, offerIndex)} />)  
                }
            else if(offer.phone.toString().toLowerCase().includes(this.state.searchfield.toString().toLowerCase())){
                offerViews.push(<Offer 
                key={offer.id} 
                offer={offer}
                currentVote={this.state.currentVotes[offerIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, offerIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, offerIndex)} />)  
                }
            else if(offer.wage.toString().toLowerCase().includes(this.state.searchfield.toString().toLowerCase())){
                offerViews.push(<Offer 
                key={offer.id} 
                offer={offer}
                currentVote={this.state.currentVotes[offerIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, offerIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, offerIndex)} />)  
                }                    
        });
        

        
        return (
            <div className="all">
            <SearchBox searchChange={this.searchText} />

            <div className="offers-container">
             
                {offerViews}
                {
                    !this.state.isLoading && this.state.offers.length === 0 ? (
                        <div className="no-offers-found">
                            <span>No Offers Found.</span>
                        </div>    
                    ): null
                }  
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-offers"> 
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus" /> Load more
                            </Button>
                        </div>): null
                }              
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null                     
                }
            </div>
            </div>

        );
    }
}

export default withRouter(AnnouncementList);
