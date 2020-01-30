import React, { Component } from 'react';
import { createOffer } from '../util/APIUtils';
import {OFFER_QUESTION_MAX_LENGTH, OFFER_CITY_MAX_LENGTH, OFFER_PHONE_MAX_LENGTH, OFFER_WAGE_MAX_LENGTH } from '../constants';
import './NewAnnouncement.css';  
import { Form, Input, Button, Icon, Select, Col, notification } from 'antd';
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input

class NewOffer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            question: {
                text: ''
            },
            city: {
                text: ''
            },
            phone: {
                text: ''
            },
            wage: {
                text: ''
            },
            offerLength: {
                days: 1,
                hours: 0
            }
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleQuestionChange = this.handleQuestionChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.handleWageChange = this.handleWageChange.bind(this);
        this.handleOfferDaysChange = this.handleOfferDaysChange.bind(this);
        this.handleOfferHoursChange = this.handleOfferHoursChange.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    
    handleSubmit(event) {
        event.preventDefault();
        const offerData = {
            question: this.state.question.text,
            city: this.state.city.text,
            phone: this.state.phone.text,
            wage: this.state.wage.text,
            offerLength: this.state.offerLength
        };

        createOffer(offerData)    
        .then(response => {
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login create offer.');    
            } else {
                notification.error({
                    message: 'Doingg App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });              
            }
        });
    }

    validateQuestion = (questionText) => {
        if(questionText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your question!'
            }
        } else if (questionText.length > OFFER_QUESTION_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Question is too long (Maximum ${OFFER_QUESTION_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }
    validateCity = (cityText) => {
        if(cityText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your City!'
            }
        } else if (cityText.length > OFFER_CITY_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `City name is too long (Maximum ${OFFER_CITY_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }
    validatePhone = (phoneText) => {
        if(phoneText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your phone number!'
            }
        } else if (phoneText.length > OFFER_PHONE_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Phone number is too long (Maximum ${OFFER_PHONE_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }
    validateWage = (wageText) => {
        if(wageText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your wage!'
            }
        } else if (wageText.length > OFFER_WAGE_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Wage is too long (Maximum ${OFFER_WAGE_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleQuestionChange(event) {
        const value = event.target.value;
        this.setState({
            question: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }
    handleCityChange(event) {
        const value = event.target.value;
        this.setState({
            city: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }
    handlePhoneChange(event) {
        const value = event.target.value;
        this.setState({
            phone: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }
    handleWageChange(event) {
        const value = event.target.value;
        this.setState({
            wage: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }

    

    handleOfferDaysChange(value) {
        const offerLength = Object.assign(this.state.offerLength, {days: value});
        this.setState({
            offerLength: offerLength
        });
    }

    handleOfferHoursChange(value) {
        const offerLength = Object.assign(this.state.offerLength, {hours: value});
        this.setState({
            offerLength: offerLength
        });
    }

    isFormInvalid() {
        if(this.state.question.validateStatus !== 'success' || this.state.city.validateStatus !== 'success' || this.state.phone.validateStatus !== 'success' || this.state.wage.validateStatus !== 'success') {
            return true;
        }
    }

    render() {
       
        return (
            <div className="new-offer-container">
                <h1 className="page-title">Create Announcement</h1>
                <div className="new-offer-content">
                    <Form onSubmit={this.handleSubmit} className="create-offer-form">
                        <FormItem validateStatus={this.state.question.validateStatus}
                            help={this.state.question.errorMsg} className="offer-form-row" label="Description:"
>
                        <TextArea 
                            placeholder="Describie your announcement"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "question"
                            value = {this.state.question.text}
                            onChange = {this.handleQuestionChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.city.validateStatus}
                            help={this.state.city.errorMsg} className="offer-form-row" label="City:"
>
                        <Input 
                            placeholder="Enter your city"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "city"
                            value = {this.state.city.text}
                            onChange = {this.handleCityChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.phone.validateStatus}
                            help={this.state.phone.errorMsg} className="offer-form-row" label="Phone number:"
>
                        <Input
                            placeholder="Enter your phone"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "phone"
                            value = {this.state.phone.text}
                            onChange = {this.handlePhoneChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.wage.validateStatus}
                            help={this.state.wage.errorMsg} className="offer-form-row" label="Wage"
>
                        <Input 
                            placeholder="Enter your wage"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "question"
                            value = {this.state.wage.text}
                            onChange = {this.handleWageChange} />
                        </FormItem>
                        <FormItem className="offer-form-row">
                            <Col xs={24} sm={7}>
                                Announcement length: 
                            </Col>
                            <Col xs={24} sm={15}>    
                                <span style = {{ marginRight: '18px' }}>
                                    <Select 
                                        name="days"
                                        defaultValue="1" 
                                        onChange={this.handleOfferDaysChange}
                                        value={this.state.offerLength.days}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(8).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Days
                                </span>
                                <span>
                                    <Select 
                                        name="hours"
                                        defaultValue="0" 
                                        onChange={this.handleOfferHoursChange}
                                        value={this.state.offerLength.hours}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(24).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Hours
                                </span>
                            </Col>
                        </FormItem>
                        <FormItem className="offer-form-row">
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                disabled={this.isFormInvalid()}
                                className="create-offer-form-button">Create announcement</Button>
                        </FormItem>
                    </Form>
                </div>    
            </div>
        );
    }
}

export default NewOffer;