import LoginForm from '../ui/LoginForm'
import { performLogin, logingIn, validateAuthentication, clearAuthenticationErrorMessage } from '../../actions/authActions'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'

const mapStateToProps = (state, props) => ({
    username: state.auth.username,
    isLoginFormVisible: state.auth.loginFormVisibility,
    errorMessage: state.auth.errorMessage,
    isLogingIn: state.auth.logingIn,
    router: props.history
})

const mapDispatchToProps = dispatch => ({
    onLogin({username, password}) {
        dispatch(logingIn(true))
        dispatch(performLogin({username, password}))
    },
    onAuthenticationValidation(props) {
        console.log("before dispatch")
        dispatch(validateAuthentication(props.location, props.history))
    },
    onClearError() {
        dispatch(clearAuthenticationErrorMessage())
    }
})

const Container = connect(mapStateToProps, mapDispatchToProps)(LoginForm)
export default withRouter(Container)