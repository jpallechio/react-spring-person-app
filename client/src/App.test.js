import React from 'react';
import ReactDOM from 'react-dom';
import { shallow } from 'enzyme';
import sinon from 'sinon';

import App from './App';
import PersonList from './PersonList';
import PersonEdit from  './PersonEdit';
import PersonAdd from './PersonAdd';
import fetchMock from 'fetch-mock';


describe('App', () => {

    beforeAll(() => {
        fetchMock.get('/api/person', [{firstName: "Nathan", lastName: "Zukoff"}], {overwriteRoutes: false})
    })

    afterAll(() => {
        fetchMock.restore();
    })

    it('renders without crashing', () => {
        const appWrapper = shallow(<App/>);
        const personList = appWrapper.find(PersonList);
        expect(personList).toHaveLength(1);
    });

    const waitForAsync = () => new Promise(resolve => setImmediate(resolve))
    it('has expected initial state', async () => {
        const appWrapper = shallow(<App/>);
        await waitForAsync();
        const result = await fetch('/api/person')
        expect(result.ok).toBe(true);
        
        expect(appWrapper.state().people).toHaveLength(1);
        expect(appWrapper.state().selectedView).toEqual('PersonList');
    });

    //const waitForAsync = () => new Promise(resolve => setImmediate(resolve))
    it('people has a length of 1', async () => {
        const appWrapper = shallow(<App/>);
        await waitForAsync();
        expect(appWrapper.state().people).toHaveLength(1);
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

    it('should update a person and change view on save', async () => {
        // Setup
        fetchMock.put('/api/person/1', 200)
        const appWrapper = shallow(<App/>);
        const expected = {firstName: 'Barbara', lastName: 'Liskov', id: 1};
        appWrapper.setState({selectedPerson: {firstName: 'Grace', lastName: 'Hopper', id: 1}});

        // Exercise
        appWrapper.instance().onSave(expected);
        await waitForAsync();

        // Assert
        expect(appWrapper.state().selectedView).toEqual('PersonList');
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

    it('should add the new person to state and change view to PersonList', async () => {
        // Setup
        fetchMock.post('/api/person', 200)
        const appWrapper = shallow(<App/>);
        const expected = {firstName: 'Barbara', lastName: 'Liskov'};
        fetchMock.get(
            '/api/person',
            [{firstName: "Different", lastName: "Response"}, {firstName: "An", lastName: "Nguyen"}],
            { overwriteRoutes: true })
   
        // Exercise
        appWrapper.instance().onSaveNew(expected);
        await waitForAsync();

        // Assert
        expect(appWrapper.state().selectedView).toEqual('PersonList');
        expect(appWrapper.state().people).toHaveLength(2);
    });

    it('should delete the person from state', async () => {
        console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Setup
        fetchMock.delete('/api/person/2', 200);
        const appWrapper = shallow(<App/>);
      
        //sinon.stub()

        const deletedPerson = {firstName: 'Alan', lastName: 'Turing', id:2};
        console.log("calling  deletePerson ", deletedPerson)
        await waitForAsync();
        // Exercise
        console.log("About to call onDelete" , deletedPerson)
        appWrapper.instance().onDelete(deletedPerson);
        await waitForAsync();
    
        appWrapper.update();
        const personList = appWrapper.find(PersonList);
//window.fetch
        // Assert
        expect(personList.props().people).not.toContainEqual(deletedPerson);
    })

});
