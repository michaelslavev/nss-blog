const initState = {
    loggedIn: false,
    user:{}
}

const rootReducer = (state = initState, action) => {

    if(action.type == 'LOGGING_IN'){
        console.log(action.user);
        return{
            ...state,
            loggedIn: true,
            user:action.user
        }
    }

    return state;
}

export default rootReducer;