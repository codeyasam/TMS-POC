import EditApplicationForm from '../../ui/applications/EditApplicationForm'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedApplication: state.application.selectedApplicationEntries[0]
})

const mapDispatchToProps = dispatch => ({
    onEditApplication(application) {
        console.log("here on edit application")
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(EditApplicationForm)