# React TDD

## Objectives

- Practice TDD workflow to create a simple CRUD app with React
- Use `Enzyme`'s `shallow` function to test React components without their child components
- Write assertions using `Jest`
- Use `sinon` to create stubs/spies

## Rationale

TDD is a requirement in modern software development. The functional nature of React, combined with helpful libraries like enzyme make the React TDD workflow very appealing. However, without practice developers might be tempted to fall back to pre-TDD ways. This exercise gives students practice with the TDD workflow and enzyme.

## Setup

1. Fork this repo.
1. Clone your fork.
1. Run `yarn install`.
1. Run `yarn test` and verify existing tests pass.
1. Run `yarn start` to view app in the browser.

## Instructions

This repo contains the List part of CRUDL. It is up to you to implement the rest, using TDD:

1. Write a test to verify that `firstName` and `lastName` are populated in `PersonEdit`.
1. Write as little code as needed to make the test pass.
1. When the test is passing, check the app in the browser to manually confirm the functionality.
1. Refactor.
1. Identify the next needed functionality and repeat the TDD workflow.

Tips:
*  Use [simulate](https://github.com/airbnb/enzyme/blob/master/docs/api/ShallowWrapper/simulate.md) to simulate text change and click events
*  Use sinon to stub the `onEdit()` call
*  Verify the stub was called with the correct arguments.

When you are satisfied with `Edit`, go on to implement `Create` and `Delete` using TDD, writing the smallest failing test, then the least code to make the test pass!

## Resources

* [Jest assertions](https://facebook.github.io/jest/docs/en/expect.html)
* [Shallow rendering with Enzyme](http://airbnb.io/enzyme/docs/api/shallow.html)
* [Shallow docs on GitHub](https://github.com/airbnb/enzyme/tree/master/docs/api/ShallowWrapper)
* [Shallow find](http://airbnb.io/enzyme/docs/api/ShallowWrapper/find.html)
* [Shallow simulate](http://airbnb.io/enzyme/docs/api/ShallowWrapper/simulate.html)
* [Sinon docs](http://sinonjs.org/releases/v4.1.6/)
