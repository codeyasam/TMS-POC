import Applications from '../../ui/applications/Applications'
import { fetchApplications, hideAddApplicationForm, showAddApplicationForm, hideErrorOnAddingApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    isAddApplicationFormVisible: state.application.addApplication.addApplicationFormVisibility,
    // selectedEntries: state.application.selectedApplicationEntries
})

const mapDispatchToProps = dispatch => ({
    onFetchApplications() {
        dispatch(fetchApplications())
    },
    onCloseAddApplicationForm() {
        console.log("on close add application form")
        dispatch(hideAddApplicationForm())
    },
    onAddApplicationButtonClick() {
        dispatch(hideErrorOnAddingApplication())
        dispatch(showAddApplicationForm())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Applications)