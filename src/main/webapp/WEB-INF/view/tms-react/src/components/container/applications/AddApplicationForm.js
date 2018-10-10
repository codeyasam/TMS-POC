import AddApplicationForm from '../../ui/applications/AddApplicationForm'
import { addApplicationRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    hasError: state.application.addApplication.hasErrorOnAddingApplication,
    isAddingApplication: state.application.addApplication.addingNewApplication,
    currentPage: state.application.pagination.paginationPage,
    pageSize: state.application.pagination.paginationSize    
})

const mapDispatchToProps = dispatch => ({
    onAddApplication(application, currentPage, pageSize) {
        dispatch(addApplicationRequest(application, currentPage, pageSize))
    }
}) 

export default connect(mapStateToProps, mapDispatchToProps)(AddApplicationForm)