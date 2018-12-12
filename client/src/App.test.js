import React from 'react';
import ReactDOM from 'react-dom';
import { shallow } from 'enzyme';

import App from './App';
import PersonList from './PersonList';
import PersonEdit from  './PersonEdit';
import PersonAdd from './PersonAdd';

describe('App', () => {
    it('renders without crashing', () => {
        const appWrapper = shallow(<App/>);
        const personList = appWrapper.find(PersonList);
        expect(personList).toHaveLength(1);
    });

    it('has expected initial state', () => {
        const appWrapper = shallow(<App/>);
        expect(appWrapper.state().people).toHaveLength(3);
        expect(appWrapper.state().selectedView).toEqual('PersonList');
    });

    it('passes props to PersonList', () => {
        const appWrapper = shallow(<App/>);
        const personList = appWrapper.find(PersonList.name);
        expect(personList.props().people).toHaveLength(3);
        expect(typeof personList.props().onEdit).toEqual('function');
        expect(typeof personList.props().onAdd).toEqual('function');
    });

    it('changes state when onEdit is invoked', () => {
        const appWrapper = shallow(<App/>);

        appWrapper.instance().onEdit();

        expect(appWrapper.state().selectedView).toEqual('PersonEdit');
    });

    it('renders the edit view when state property is PersonEdit', () => {
        const appWrapper = shallow(<App/>);
        appWrapper.setState({selectedView: 'PersonEdit'});
        expect(appWrapper.find(PersonEdit)).toHaveLength(1);
    });

    it('passes props to PersonEdit', () => {
        const appWrapper = shallow(<App/>);
        appWrapper.setState({selectedPerson: {name: 'test'}});
        appWrapper.setState({selectedView: 'PersonEdit'});
        const personEdit = appWrapper.find(PersonEdit);

        expect(personEdit.props().selectedPerson).toEqual({name: 'test'});
    });

    it('should update a person and change view on save', () => {
        // Setup
        const appWrapper = shallow(<App/>);
        const expected = {firstName: 'Barbara', lastName: 'Liskov'};
        const personList = appWrapper.find(PersonList);
        appWrapper.setState({selectedPerson: {firstName: 'Grace', lastName: 'Hopper'}});
        appWrapper.setState({selectedView: 'PersonEdit'});

        // Exercise
        appWrapper.instance().onSave(expected);

        // Assert
        expect(appWrapper.state().selectedView).toEqual('PersonList');
        expect(personList.props().people).toContainEqual(expected);
    });

    it('changes state when onAdd is invoked', () => {
        // Setup
        const appWrapper = shallow(<App/>);

        // Exercise
        appWrapper.instance().onAdd();

        // Assert
        expect(appWrapper.state().selectedView).toEqual('PersonAdd');
    });

    it('renders the add view when state property is PersonAdd', () => {
        // Setup
        const appWrapper = shallow(<App/>);
        appWrapper.setState({selectedView: 'PersonAdd'});

        // Assert
        expect(appWrapper.find(PersonAdd)).toHaveLength(1);
    });

    it('should add the new person to state and change view to PersonList', () => {
        // Setup
        const appWrapper = shallow(<App/>);
        const expected = {firstName: 'Barbara', lastName: 'Liskov'};
        appWrapper.setState({selectedView: 'PersonAdd'});

        // Exercise
        appWrapper.instance().onSaveNew(expected);
        appWrapper.update();
        const personList = appWrapper.find(PersonList);

        // Assert
        expect(appWrapper.state().selectedView).toEqual('PersonList');
        expect(personList.props().people).toContainEqual(expected);
    });

    it('should delete the person from state', () => {
        // Setup
        const appWrapper = shallow(<App/>);
        const deletedPerson = {firstName: 'Alan', lastName: 'Turing'};

        // Exercise
        appWrapper.instance().onDelete(deletedPerson);
        appWrapper.update();
        const personList = appWrapper.find(PersonList);

        // Assert
        expect(personList.props().people).not.toContainEqual(deletedPerson);
    })

});
