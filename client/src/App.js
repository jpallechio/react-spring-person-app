import React, { Component } from 'react';
import PersonList from './PersonList';
import PersonEdit from './PersonEdit';
import PersonAdd from "./PersonAdd";

export default class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            people: [],
            selectedView: 'PersonList',
            selectedPerson: undefined
        };
    }

    componentDidMount() {
        this.showList()
    }

    showList = async () => {
        let body = await fetch('/api/person')
        console.log("The body is: ", body)
        let people = await body.json();
        console.log("The people are: ", people)
        this.setState(
            {
                people, selectedView: 'PersonList'
            }
        )
    }

    onAdd = () => {
        this.setState({ ...this.state, selectedView: 'PersonAdd' });
    };

    onEdit = (p) => {
        this.setState({ ...this.state, selectedView: 'PersonEdit', selectedPerson: p });
    };

    onSave = async (updatedPerson) => {
        console.log("The updated person is: ", this.state.selectedPerson);
        console.log("new person info ", updatedPerson)
        let updatedId = updatedPerson.id;
        updatedPerson = JSON.stringify(updatedPerson);
        //console.log("Updated person id is: ", updatedId)
        var response = await fetch('/api/person/' + this.state.selectedPerson.id, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "PUT",
            body: updatedPerson
        })
        this.showList();
    };

    onDelete = async (deletedPerson) => {
        var response = await fetch('/api/person/' + deletedPerson.id,
            {
                method: "DELETE"
            })
        this.showList();

    };

    onSaveNew = async (newP) => {
        newP = JSON.stringify(newP);
        var response = await fetch('/api/person', {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: newP
        })
        this.showList();
    };

    get currentView() {
        if (this.state.selectedView === 'PersonEdit') {
            return <PersonEdit selectedPerson={this.state.selectedPerson} onSave={this.onSave} />
        } else if (this.state.selectedView === 'PersonAdd') {
            return <PersonAdd onSave={this.onSaveNew} />
        } else {
            return <PersonList people={this.state.people}
                onEdit={this.onEdit}
                onAdd={this.onAdd}
                onDelete={this.onDelete} />
        }
    }

    render() {
        return (
            <div className="App">
                {this.currentView}
            </div>
        );
    }
}