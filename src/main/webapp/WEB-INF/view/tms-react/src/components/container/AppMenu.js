import AppMenu from '../ui/AppMenu'
import { performLogout } from '../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
})

const mapDispatchToProps = (dispatch) => ({
    onLogout() {
        dispatch(performLogout())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(AppMenu)