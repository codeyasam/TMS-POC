import Applications from '../../ui/applications/Applications'
import { fetchApplications, resetSuccessfullyAddApplication, resetSuccessfullyEditApplication, resetSuccessfullyDeleteApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    applicationList: state.application.applicationList,
    isSuccessfullyAdded: state.application.addApplication.successfullyAddedApplication,
    isSuccessfullyEdited: state.application.editApplication.successfullyEditedApplication,
    isSuccessfullyDeleted: state.application.deleteApplication.successfullyDeletedApplication
})

const mapDispatchToProps = dispatch => ({
    onFetchApplications() {
        dispatch(fetchApplications())
    }, 
    onPromptModalClose() {
        dispatch(resetSuccessfullyAddApplication())        
        dispatch(resetSuccessfullyEditApplication())
        dispatch(resetSuccessfullyDeleteApplication())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Applications)