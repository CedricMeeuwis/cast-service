name: Run tests

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        node-version: [12.x, 14.x]
        mongodb-version: [4.0, 4.2, 4.4]

    steps:
    - name: Git checkout
      uses: actions/checkout@v2

    - name: Use Node.js ${{ matrix.node-version }}
      uses: actions/setup-node@v1
      with:
        node-version: ${{ matrix.node-version }}

    - name: Start MongoDB
      uses: supercharge/mongodb-github-action@1.3.0
      with:
        mongodb-version: ${{ matrix.mongodb-version }}

    - run: npm install

    - run: npm test
      env:
        CI: true