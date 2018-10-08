import AddApplicationForm from '../../ui/applications/AddApplicationForm'
import { addApplicationRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    hasError: state.application.addApplication.hasErrorOnAddingApplication,
    isAddingApplication: state.application.addApplication.addingNewApplication
})

const mapDispatchToProps = dispatch => ({
    onAddApplication(application) {
        dispatch(addApplicationRequest(application))
    }
}) 

export default connect(mapStateToProps, mapDispatchToProps)(AddApplicationForm)