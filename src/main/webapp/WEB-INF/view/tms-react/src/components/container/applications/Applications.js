import Applications from '../../ui/applications/Applications'
import { fetchApplications } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
})

const mapDispatchToProps = dispatch => ({
    onFetchApplications() {
        dispatch(fetchApplications())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Applications)