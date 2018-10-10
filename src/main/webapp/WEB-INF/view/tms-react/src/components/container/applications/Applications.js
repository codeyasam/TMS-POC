import Applications from '../../ui/applications/Applications'
import { fetchApplications, resetSuccessfullyAddApplication, resetSuccessfullyEditApplication, resetSuccessfullyDeleteApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    applicationList: state.application.applicationList,
    isSuccessfullyAdded: state.application.addApplication.successfullyAddedApplication,
    isSuccessfullyEdited: state.application.editApplication.successfullyEditedApplication,
    isSuccessfullyDeleted: state.application.deleteApplication.successfullyDeletedApplication,
    currentPage: state.application.pagination.paginationPage,
    pageSize: state.application.pagination.paginationSize
})

const mapDispatchToProps = dispatch => ({
    onFetchApplications({ currentPage, pageSize }) {
        dispatch(fetchApplications(currentPage, pageSize))
    }, 
    onPromptModalClose() {
        dispatch(resetSuccessfullyAddApplication())        
        dispatch(resetSuccessfullyEditApplication())
        dispatch(resetSuccessfullyDeleteApplication())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Applications)