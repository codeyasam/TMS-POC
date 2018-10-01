import LoginForm from '../ui/LoginForm'
import { login, validateAuthentication, clearAuthenticationErrorMessage } from '../../actions'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'

const mapStateToProps = (state, props) => ({
    username: state.auth.username,
    isLoginFormVisible: state.auth.loginFormVisibility,
    errorMessage: state.auth.errorMessage,
    router: props.history
})

const mapDispatchToProps = dispatch => ({
    onLogin({username, password}) {
        dispatch(login({username, password}))
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